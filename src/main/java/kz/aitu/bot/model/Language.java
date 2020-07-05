package kz.aitu.bot.model;

public enum Language {
    KAZ("kz"), ENG("en"), RUS("ru");

    String language;

    Language(String language) {
        this.language = language;
    }

    public String getLang() {
        return language;
    }

    public static Language convert(String st) {
        switch (st) {
            case "kz": return Language.KAZ;
            case "en": return Language.ENG;
            default: return Language.RUS;
        }
    }
}
