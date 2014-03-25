package com.ruyicai.controller.service;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;
import com.ruyicai.activity.buy.nmk3.TransParentActivity;
import com.ruyicai.controller.listerner.AnimationListener;
import com.ruyicai.util.PublicMethod;

@Singleton
public class AnimationService {

	private final List<AnimationListener> animationListeners = new ArrayList<AnimationListener>();
	
	public void stopAnimation() {
		for (AnimationListener animationListener : animationListeners) {
			animationListener.stopAnimation();
		}
	}
	
	public void addAnimationListeners(
			AnimationListener animationListener) {
		if (animationListeners.contains(animationListener)) {
			return;
		}
		animationListeners.add(animationListener);
	}

	public void removeAnimationListeners(
			AnimationListener animationListener) {
		if (animationListeners.contains(animationListener)) {
			animationListeners.remove(animationListener);
		}
	}
}
