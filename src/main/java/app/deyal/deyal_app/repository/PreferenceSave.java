package app.deyal.deyal_app.repository;

import java.util.prefs.Preferences;

public class PreferenceSave {
    private final Preferences preferences;

    private final String token = "TOKEN";
    private final String theme = "DarkTheme";

    public PreferenceSave() {
        this.preferences = Preferences.userRoot().node(this.getClass().getName());
    }

    public static PreferenceSave getInstance() {
        return Singleton.INSTANCE;
    }

    public String getToken() {
        return getString(token);
    }

    public void setToken(String token) {
        if (token != null)
            setString(this.token, token);
        else
            remove(this.token);
    }

    public boolean isDarkTheme() {
        return getBoolean(this.theme);
    }

    public boolean setTheme(boolean darkEnabled) {
        return setBoolean(this.theme, darkEnabled);
    }

    private String getString(String key) {
        if (key == null)
            return null;
        return preferences.get(key, null);
    }

    private boolean setString(String key, String value) {
        if (key == null || value == null ||
                key.length() > Preferences.MAX_KEY_LENGTH ||
                value.length() > Preferences.MAX_VALUE_LENGTH)
            return false;
        preferences.put(key, value);
        return true;
    }

    private void remove(String key) {
        if (key == null)
            return;
        preferences.remove(key);
    }

    private boolean getBoolean(String key) {
        if (key == null)
            return false;
        return preferences.getBoolean(key, false);
    }

    private boolean setBoolean(String key, boolean value) {
        if (key == null || key.length() > Preferences.MAX_KEY_LENGTH)
            return false;
        preferences.putBoolean(key, value);
        return true;
    }

    private static class Singleton {
        private static final PreferenceSave INSTANCE = new PreferenceSave();
    }
}
