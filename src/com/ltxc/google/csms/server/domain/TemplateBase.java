package com.ltxc.google.csms.server.domain;

import java.util.Map;

import javax.xml.bind.Unmarshaller;

public interface TemplateBase {
	public Map<String, Object> getMappedValues();
	public void afterUnmarshal(Unmarshaller u, Object parent);
}
