package com.bytesmyth.graphics.animation;

import java.util.HashMap;

public class Animation {

    private HashMap<String, AnimationTimeline> timelines = new HashMap<>();

    public Animation(AnimationTimeline... timelines) {
        for (AnimationTimeline timeline : timelines) {
            this.timelines.put(timeline.getName().toLowerCase(), timeline);
        }
    }

    public AnimationTimeline getTimeline(String name) {
        return timelines.get(name.toLowerCase());
    }

}
