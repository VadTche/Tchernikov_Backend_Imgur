package imgur.src.main;

public enum Images {
    IMAGE_JPG_ORDINARY("src/test/resources/Candyman.jpeg", "image/jpeg"),
    IMAGE_JPG_SMALL("src/test/resources/Big_name_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Fantasy_Worlds_(1600x900).jpeg", "image/jpeg"),
    IMAGE_JPG_HD("src/test/resources/Bullets_(3840x2160).jpeg", "image/jpeg"),
    IMAGE_JPG_OVER_SIZE("src/test/resources/foto_21mb.jpeg", "image/jpeg"),
    NON_IMAGE_PDF("src/test/resources/patroni.pdf", "image/pdf"),
    IMAGE_GIF("src/test/resources/@*&%$#.gif", "image/gif"),
    IMAGE_BMP("src/test/resources/S.bmp", "image/bmp"),
    IMAGE_PNG("src/test/resources/Человек_Паук.png", "image/png"),
    IMAGE_PNG_LESS_ONE_KB("src/test/resources/black-and-white.png", "image/png"),
    IMAGE_TIFF("src/test/resources/Example.tiff", "image/tiff"),
    IMAGE_URL("https://storge.pic2.me/c/1360x800/251/61351405e08d91.35356004.jpg", "image/url");

    private final String path;
    private final String format;
    Images(String path, String format) {
        this.path = path;
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public String getPath() {
        return path;
    }
}
