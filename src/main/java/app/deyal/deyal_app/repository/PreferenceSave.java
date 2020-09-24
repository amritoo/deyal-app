package app.deyal.deyal_app.repository;

import java.util.prefs.Preferences;

public class PreferenceSave {
    private final Preferences preferences;

    private final String token = "TOKEN";

    public String getToken() {
        return preferences.get(token, null);
    }

    public void setToken(String token) {
        if (token == null)
            preferences.remove(this.token);
        else
            preferences.put(this.token, token);
    }

    public String getString(String key) {
        if (key == null)
            return null;
        return preferences.get(key, null);
    }

    public boolean setString(String key, String value) {
        if (key == null || value == null ||
                key.length() > Preferences.MAX_KEY_LENGTH ||
                value.length() > Preferences.MAX_VALUE_LENGTH)
            return false;
        preferences.put(key, value);
        return true;
    }

    public void remove(String key) {
        if (key == null)
            return;
        preferences.remove(key);
    }

    public boolean getBoolean(String key) {
        if (key == null)
            return false;
        return preferences.getBoolean(key, false);
    }

    public boolean setBoolean(String key, boolean value) {
        if (key == null || key.length() > Preferences.MAX_KEY_LENGTH)
            return false;
        preferences.putBoolean(key, value);
        return true;
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
