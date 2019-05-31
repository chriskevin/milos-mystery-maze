package se.chriskevin.mysterymaze.animation;


import io.vavr.collection.List;

import java.awt.Image;

public class ImageSequence {

    private Integer index;

    private List<Image> sequence;

    public ImageSequence(final List<Image> sequence) {
        this.sequence = sequence;
        this.index = 0;
    }

    public List<Image> getSequence() {
        return this.sequence;
    }

    public Image next() {
        if (index + 1 <= sequence.size()) {
            index++;
        }
        return getCurrent();
    }

    public Image previous() {
        if (index - 1 >= 0) {
            index--;
        }
        return getCurrent();
    }

    public Image reset() {
        index = 0;
        return getCurrent();
    }

    private Image getCurrent() {
        return sequence.get(index);
    }

    public void setSequence(final List<Image> sequence) {
        this.sequence = sequence;
    }
}
