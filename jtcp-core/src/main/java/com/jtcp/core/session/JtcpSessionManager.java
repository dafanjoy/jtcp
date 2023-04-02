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
	// 客户端链接sessionID和Session的对应关系
	private Map<String, JtcpSession> sessionIdMap;

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
	}

	public boolean containsKey(String sessionId) {
		return sessionIdMap.containsKey(sessionId);
	}

	public boolean containsSession(JtcpSession session) {
		return sessionIdMap.containsValue(session);
	}

	public JtcpSession findBySessionId(String sessionId) {
		return sessionIdMap.get(sessionId);
	}


	public synchronized JtcpSession put(String key, JtcpSession value) {
		return sessionIdMap.put(key, value);
	}

	public synchronized JtcpSession removeBySessionId(String sessionId) {
		if (sessionId == null)
			return null;
		JtcpSession session = sessionIdMap.remove(sessionId);
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