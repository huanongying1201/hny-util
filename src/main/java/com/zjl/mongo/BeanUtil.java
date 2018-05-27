package com.zjl.mongo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

public class BeanUtil {

	/**
	 * 把实体bean对象转换成DBObject
	 * 
	 * @param bean
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Document bean2Doc(Object bean) throws IllegalArgumentException, IllegalAccessException {
		if (bean == null) {
			return null;
		}
		Document dbObject = new Document();
		// 获取对象对应类中的所有属性域
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			// 获取属性名
			String varName = field.getName();
			// 修改访问控制权限
			boolean accessFlag = field.isAccessible();
			if (!accessFlag) {
				field.setAccessible(true);
			}
			Object param = field.get(bean);
			if (param == null) {
				continue;
			} else if (param instanceof Integer) {// 判断变量的类型
				int value = ((Integer) param).intValue();
				dbObject.put(varName, value);
			} else if (param instanceof String) {
				String value = (String) param;
				dbObject.put(varName, value);
			} else if (param instanceof Double) {
				double value = ((Double) param).doubleValue();
				dbObject.put(varName, value);
			} else if (param instanceof Float) {
				float value = ((Float) param).floatValue();
				dbObject.put(varName, value);
			} else if (param instanceof Long) {
				long value = ((Long) param).longValue();
				dbObject.put(varName, value);
			} else if (param instanceof Boolean) {
				boolean value = ((Boolean) param).booleanValue();
				dbObject.put(varName, value);
			} else if (param instanceof Date) {
				Date value = (Date) param;
				dbObject.put(varName, value);
			} else if (param instanceof ObjectId) {
				ObjectId value = (ObjectId) param;
				dbObject.put(varName, value);
			}
			// 恢复访问控制权限
			field.setAccessible(accessFlag);
		}
		return dbObject;
	}

	/**
	 * 把document对象转换成bean对象
	 * 
	 * @param doc
	 * @param bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException 
	 * @throws InstantiationException 
	 */
	public static Object doc2Bean(Document doc, Class clazz) throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException {
		if (clazz == null) {
			return null;
		}
		Object o = clazz.newInstance();
		Field[] fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			String varName = field.getName();
			Object object = doc.get(varName);
			if (object != null) {
				BeanUtils.setProperty(o, varName, object);
			}
		}
		return o;
	}

	/**
	 * 把document对象转换成对象数组
	 * 
	 * @param list
	 * @param bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static List<Object> doc2List(List<Document> list, Class clazz)
			throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException {
		List li = new ArrayList<>();
		for (Document doc : list) {
			Object o = doc2Bean(doc, clazz);
			li.add(o);
		}
		return li;
	}
}