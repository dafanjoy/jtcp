package com.jtcp.core.session;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;


public class JtcpSessionManager {

	private static volatile JtcpSessionManager instance = null;
	// netty生成的sessionID和Session的对应关系
	private Map<String, JtcpSession> sessionIdMap;
	// 终端手机号和netty生成的sessionID的对应关系
	private Map<String, String> TerminalMap;

	public static JtcpSessionManager getInstance() {
		if (instance == null) {
			synchronized (JtcpSessionManager.class) {
				if (instance == null) {
					instance = new JtcpSessionManager();
				}
			}
		}
		return instance;
	}

	public JtcpSessionManager() {
		this.sessionIdMap = new ConcurrentHashMap<>();
		this.TerminalMap = new ConcurrentHashMap<>();
	}

	public boolean containsKey(String sessionId) {
		return sessionIdMap.containsKey(sessionId);
	}

	public boolean containsSession(JtcpSession session) {
		return sessionIdMap.containsValue(session);
	}

	public JtcpSession findBySessionId(String id) {
		return sessionIdMap.get(id);
	}

	public JtcpSession findByTerminalPhone(String phone) {
		String sessionId = this.TerminalMap.get(phone);
		if (sessionId == null)
			return null;
		return this.findBySessionId(sessionId);
	}

	public synchronized JtcpSession put(String key, JtcpSession value) {
		if (value.getSessionNo() != null && !"".equals(value.getSessionNo().trim())) {
			this.TerminalMap.put(value.getSessionNo(), value.getId());
		}
		return sessionIdMap.put(key, value);
	}

	public synchronized JtcpSession removeBySessionId(String sessionId) {
		if (sessionId == null)
			return null;
		JtcpSession session = sessionIdMap.remove(sessionId);
		if (session == null)
			return null;
		//session为空，且通过终端号查找的sessionID与当前sessionID一致才能表明该sessionID是最新的，否则只能说明该设备终端又建立了新的连接，而旧连接没有正常关闭
		if (session.getSessionNo() != null&&TerminalMap.get(session.getSessionNo()).equals(sessionId))
			this.TerminalMap.remove(session.getSessionNo());
		return session;
	}

	public Set<String> keySet() {
		return sessionIdMap.keySet();
	}

	public void forEach(BiConsumer<? super String, ? super JtcpSession> action) {
		sessionIdMap.forEach(action);
	}

	public Set<Entry<String, JtcpSession>> entrySet() {
		return sessionIdMap.entrySet();
	}

	public List<JtcpSession> toList() {
		return this.sessionIdMap.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
	}

}