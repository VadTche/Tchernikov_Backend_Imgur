package imgur.src.main;

public enum Images {
    IMAGE_GIF("src/test/resources/@*&%$#.gif"),
    IMAGE_JPG_BIG("src/test/resources/Bullets (3840x2160).jpeg"),
    IMAGE_JPG_SMALL("src/test/resources/Candyman Кэндимен (600x950).jpeg"),
    IMAGE_JPG_AVERAGE("src/test/resources/Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Fantasy Worlds (1600x900).jpeg"),
    NON_IMAGE_PDF("src/test/resources/patroni.pdf"),
    IMAGE_BMP("src/test/resources/S.bmp"),
    IMAGE_PNG("src/test/resources/Spiderman.png"),
    IMAGE_URL("https://refactoring.guru/images/content-public/logos/logo-covid-2x.png");

    private String path;

    Images(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
