package view;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class DiceAnimation extends Transition {

    final int N;
    final ImageView imageView;

    public DiceAnimation(int n, ImageView imageView, Duration duration) {
        super();
        N = n;
        this.imageView = imageView;
        setCycleDuration(duration);
        setCycleCount(4);
        setInterpolator(Interpolator.LINEAR);
        imageView.setViewport(new Rectangle2D(0, 0, 256, 256));
    }

    @Override
    protected void interpolate(double frac) {
        final int index = (int) (frac * (N - 1));
        imageView.setViewport(new Rectangle2D(index * 256, 0, 256, 256));
    }
}
