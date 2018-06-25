package com.mountainframework.config.container;

public class MountainConfigContainer {

	public static MountainConfigContainer getInstance() {
		return MountainConfigContainerHolder.INSTANCE;
	}

	private MountainConfigContainer() {
	}

	private static class MountainConfigContainerHolder {
		public static final MountainConfigContainer INSTANCE = new MountainConfigContainer();
	}

}
