package com.star.thread.threadsecurity;

import java.util.HashMap;
import java.util.Map;

/**
 * 发布逸出
 * 利用副本解决问题
 *
 * @Author: zzStar
 * @Date: 10-19-2020 11:55
 */
public class ReleaseEscape {

    // 这里定义为private
    private Map<String, String> states;

    public ReleaseEscape() {
        states = new HashMap<>();
        states.put("1", "a");
        states.put("2", "b");
        states.put("3", "c");
    }

    public Map<String, String> getStates() {
        // 这里把private发布了
        return states;
    }

    public Map<String, String> getStatesImproved() {
        return new HashMap<>(states);
    }

    public static void main(String[] args) {
        ReleaseEscape releaseEscape = new ReleaseEscape();
        Map<String, String> states = releaseEscape.getStates();
/*
        System.out.println(states.get("1"));
        // 被篡改
        states.remove("1");
        // null
        System.out.println(states.get("1"));
*/
        System.out.println(releaseEscape.getStatesImproved().get("1"));
        releaseEscape.getStatesImproved().remove("1");
        // Copied
        System.out.println(releaseEscape.getStatesImproved().get("1"));
    }
}
