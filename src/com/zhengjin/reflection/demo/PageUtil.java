package com.zhengjin.reflection.demo;

import java.lang.reflect.Field;

public class PageUtil {

	private static void initialLevelInfo(Object obj, int level) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(FindBy.class) && field.getType().equals(TestInfo.class)) {
				FindBy findby = field.getAnnotation(FindBy.class);
				if (findby.level() == level) {
					TestInfo tmp = new TestInfo();
					tmp.setName(findby.name());
					tmp.setAge(findby.age());
					field.setAccessible(true);
					try {
						field.set(obj, tmp);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void initialLevel(Object obj) {
		initialLevelInfo(obj, 0);
	}

	public static void initialLevel(Object obj, int level) {
		initialLevelInfo(obj, level);
	}
}
