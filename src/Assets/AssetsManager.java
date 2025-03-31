package Assets;

import javax.swing.ImageIcon;

import java.util.*;

public class AssetsManager {
    private Map<String, ImageIcon> tileImages;

    public AssetsManager() {
        tileImages = new HashMap<>();
    }

    /**
     * 加载所有麻将牌贴图，文件位于 /Assets/mahjong/ 下
     * 文件名格式为 "Wan1.png" ... "Wan9.png", "Tiao1.png" ... "Tiao9.png", "Tong1.png" ... "Tong9.png"
     */
    public void loadAssets() {
        String[] suits = {"Wan", "Tiao", "Tong"};
        for (String suit : suits) {
            for (int i = 1; i <= 9; i++) {
                String key = suit + i;
                String filename = "/Assets/mahjong/" + key + ".png";
                ImageIcon icon = new ImageIcon(getClass().getResource(filename));
                tileImages.put(key, icon);
                // 若加载失败，可打印提示
                if (icon.getImageLoadStatus() != java.awt.MediaTracker.COMPLETE) {
                    System.out.println("加载贴图失败: " + filename);
                }
            }
        }
    }

    /**
     * 根据牌花色和数字获取对应的贴图
     * @param suit   "Wan", "Tiao", "Tong"
     * @param number 牌号 1-9
     * @return 对应的 ImageIcon 对象
     */
    public ImageIcon getTileImage(String suit, int number) {
        return tileImages.get(suit + number);
    }
}