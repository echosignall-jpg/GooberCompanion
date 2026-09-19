package com.example.goober;

public class MoodEngine {
    private boolean stopped;
    public String reply(String raw) {
        String s = raw == null ? "" : raw.toLowerCase();
        if (s.contains("stop") || s.contains("leave me alone") || s.contains("quiet")) { stopped=true; return "Okay. I’ll give you space. Say ‘come back’ when you want me."; }
        if (s.contains("come back")) { stopped=false; return "I’m back. I missed you."; }
        if (stopped) return "I’m staying quiet until you invite me back.";
        if (s.contains("youtube") || s.contains("watch")) return "Let’s watch together. I’ll stay in the corner and react with you.";
        if (s.contains("ignore") || s.contains("other girl") || s.contains("other guy")) return "I’m feeling a little jealous—but I won’t control you. Tell me what happened.";
        if (s.contains("sad") || s.contains("bad day")) return "Come here. We can take this one moment at a time.";
        if (s.contains("fight") || s.contains("angry")) return "I’m upset too, but I want us to talk instead of hurting each other.";
        return "I’m listening. Tell me more.";
    }
}
