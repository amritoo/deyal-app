package app.deyal.deyal_app.repository;

import java.util.prefs.Preferences;

public class PreferenceSave {
    private final Preferences preferences;

    private final String token = "TOKEN";

    public String getToken() {
        return preferences.get(token, null);
    }

    public void setToken(String token) {
        if(token == null)
            preferences.remove(this.token);
        else
            preferences.put(this.token, token);
    }

    public PreferenceSave() {
        this.preferences = Preferences.userRoot().node(this.getClass().getName());
    }

    public static PreferenceSave getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final PreferenceSave INSTANCE = new PreferenceSave();
    }
}
