package com.jtcp.common.utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @version 创建时间：2019年4月4日 下午5:35:10
 */
public class ReflectUtils {
	/**
	 * get All classes
	 *
	 * @param packageName
	 * @return
	 * @throws Exception
	 */
	public static Set<Class<?>> getClasses(String packageName) throws Exception {
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageDirName = packageName.replace('.', '/');
		// 声明一个Class类集合
		Set<Class<?>> classSet = new HashSet<>(32);
		// 定义一个枚举的集合 并进行循环来处理这个目录
		Enumeration<URL> dirs;
		try {
			// 得到packageDirName的绝对URI路径。
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			// 遍历
			while (dirs.hasMoreElements()) {
				// 获取一个URL
				URL url = dirs.nextElement();
				// 获取Protocol
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {// 如果是一个文件
					// 获取到这个文件的路径信息
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath, recursive, classSet);
				} else if ("jar".equals(protocol)) {// 如果是一个jar包
					// 创建一个JarFile对象
					JarFile jar;
					try {
						// 获取jar对象
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						// 从jar包中获取一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						while (entries.hasMoreElements()) {// 遍历枚举
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							// 拿到JarEntry的name
							String name = entry.getName();
							if (name.charAt(0) == '/') {// 如果是以/开头的
								// 获取后面的字符串
								name = name.substring(1);
							}
							if (name.startsWith(packageDirName)) {// 如果前半部分和定义的包名相同
								int idx = name.lastIndexOf('/');
								if (idx != -1) {// 如果以"/"结尾 是一个包
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx).replace('/', '.');
								}
								if ((idx != -1) || recursive) { // 如果可以迭代下去 并且是一个包
									if (name.endsWith(".class") && !entry.isDirectory()) {// 去掉后面的".class" 获取真正的类名
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											// 添加到classSet中
											classSet.add(Class.forName(packageName + '.' + className));
										} catch (ClassNotFoundException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classSet;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName 包名
	 * @param packagePath 包路径
	 * @param recursive   是否循环迭代
	 * @param classes     class集合
	 */
	private static void findAndAddClassesInPackageByFile(String packageName, String packagePath,
			final boolean recursive, Set<Class<?>> classSet) {
		// 根据传入包的路径 建立一个File
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {// 如果不存在或者 也不是目录就直接返回
			return;
		}
		// 获取包下的所有文件 包括目录
		File[] files = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));
		// 循环所有文件
		for (File file : files) {
			if (file.isDirectory()) {//如果仍然是文件目录，继续扫描
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive,
						classSet);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					//添加到classSet集合中
					classSet.add(
							Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {

				}
			}
		}
	}

}
