package com.ybase.wrapper.demowin;

public class Main {

	public static Resource getRes() {
		return new Resource();
	}

	public static class Resource {
		public static String getString(String str, Integer num) {
			return str;
		}

		public static String getString(String str) {
			return str;
		}

		public static String getString(String str, Object obj) {
			return str;
		}
	}

}
