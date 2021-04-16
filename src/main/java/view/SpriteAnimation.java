package view;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

import javafx.util.Duration;

import java.util.ArrayList;

public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final ArrayList<Rectangle2D> frames;


    public SpriteAnimation(ImageView imageView, Duration duration, ArrayList<Rectangle2D> frames) {
        this.imageView = imageView;
        this.frames = frames;
        this.setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
        this.imageView.setViewport(frames.get(0));
    }

    @Override
    protected void interpolate(double frac) {
        final int index = (int) (frac * frames.size());
        imageView.setViewport(frames.get(index));
    }
}
