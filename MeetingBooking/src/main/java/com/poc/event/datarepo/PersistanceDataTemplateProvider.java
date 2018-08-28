package com.poc.event.datarepo;

import org.springframework.core.env.Environment;

public interface PersistanceDataTemplateProvider<T> {
	void loadTemplate(Environment et);
	T getTemplate();
}
