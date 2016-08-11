package gameModel;

public abstract class AnimationGameComponent extends GameComponent {

	protected Animation animation;
	protected int[] gameComponentAnimationDurations;

	@Override
	public Animation getAnimationClass() {
		return this.animation;
	}

	@Override
	public int[] getAnimationDurations() {

		return gameComponentAnimationDurations;
	}

	@Override
	public void animate() {
		animation.animate(10);
	}
}
