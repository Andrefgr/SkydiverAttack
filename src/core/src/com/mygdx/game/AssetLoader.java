package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class AssetLoader {
    public static TextureRegion[] gun = new TextureRegion[4];
    public static Animation<TextureRegion> gunAnimation;

    public static TextureRegion[] plane = new TextureRegion[3];
    public static Animation<TextureRegion> planeAnimation;

    public static Texture gunTexture, points, planeTexture, enemy2Texture, reload, pauseTexture, resumeTexture, fluidTexture, holeTexture;
    public static TextureRegion gunfIXED;
    public static Music music, planeSound, introSound, gameOverSound, reloadSound, clickSound, winSound, ambience;
    public static BitmapFont fontMenu, font, fontLoading, fontGameOver, fontReload, fontScore, fontReady;
    public static Label.LabelStyle labelStyle, menulabelStyle, loadingLabelStyle,
            reloadLabelStyle, gameOverLabelStyle, scoresLabelStyle, readylabelStyle;
    public static ParticleEffect particleEffect;

    public static void load() {

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("Party.party"), Gdx.files.internal(""));

        gunTexture = new Texture("gunAnima.png");
        enemy2Texture = new Texture("enemy2.png");
        points = new Texture("icon.png");
        reload = new Texture("RELOAD.png");
        gunfIXED = new TextureRegion(gunTexture, 0, 0, 198, 143);
        pauseTexture = new Texture("pause.png");
        resumeTexture = new Texture("resume.png");
        fluidTexture = new Texture("smoke.png");
        holeTexture = new Texture("hole.png");
        //MainMenu
        fontMenu = new BitmapFont(Gdx.files.internal("fontMenu.fnt"));
        fontMenu.getData().setScale(3.0f, 3.0f);
        menulabelStyle = new Label.LabelStyle();
        menulabelStyle.font = fontMenu;
        menulabelStyle.fontColor = Color.WHITE;

        //loading screen
        fontLoading = new BitmapFont(Gdx.files.internal("fontwar.fnt"));
        fontLoading.getData().setScale(3.0f, 3.0f);
        loadingLabelStyle = new Label.LabelStyle();
        loadingLabelStyle.font = fontLoading;
        loadingLabelStyle.fontColor = Color.RED;


        //GET READY
        fontReady = new BitmapFont(Gdx.files.internal("font.fnt"));
        fontReady.getData().setScale(2, 2);
        readylabelStyle = new Label.LabelStyle();
        readylabelStyle.font = fontReady;
        readylabelStyle.fontColor = Color.BLACK;

        //score
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.getData().setScale(2, 2);
        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        //gameover screen
        fontGameOver = new BitmapFont(Gdx.files.internal("fontwar.fnt"));
        fontGameOver.getData().setScale(4.0f, 4.0f);
        gameOverLabelStyle = new Label.LabelStyle();
        gameOverLabelStyle.font = fontGameOver;
        gameOverLabelStyle.fontColor = Color.RED;

        //reload screen
        fontReload = new BitmapFont(Gdx.files.internal("font.fnt"));
        fontReload.getData().setScale(3.0f, 3.0f);
        reloadLabelStyle = new Label.LabelStyle();
        reloadLabelStyle.font = fontReload;
        reloadLabelStyle.fontColor = Color.BLACK;

        //victory score
        fontScore = new BitmapFont(Gdx.files.internal("veteran.fnt"));
        fontScore.getData().setScale(3.0f, 3.0f);
        scoresLabelStyle = new Label.LabelStyle();
        scoresLabelStyle .font = fontScore;
        scoresLabelStyle .fontColor = Color.WHITE;

        loadGunAnimation();
//        reloadGunAnimation();

        //Musica
        music = Gdx.audio.newMusic(Gdx.files.getFileHandle("shooting1.mp3", Files.FileType
                .Internal));
        music.setVolume(20f);

        planeSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("sound_plain.mp3", Files.FileType
                .Internal));
        planeSound.setVolume(10f);

        introSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("sound_begin.mp3", Files.FileType
                .Internal));
        introSound.setVolume(10.0f);

        gameOverSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("gameoverSound.mp3", Files
                .FileType.Internal));
        gameOverSound.setVolume(20.0f);

        winSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("win.mp3", Files
                .FileType.Internal));
        winSound.setVolume(20.0f);

        reloadSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("reload.mp3", Files.FileType
                .Internal));
        reloadSound.setVolume(20f);

        clickSound = Gdx.audio.newMusic(Gdx.files.getFileHandle("click.mp3", Files.FileType
                .Internal));
        clickSound.setVolume(20f);

        ambience = Gdx.audio.newMusic(Gdx.files.getFileHandle("ambience.mp3", Files.FileType
                .Internal));
        ambience.setVolume(20f);
    }

    public static void loadGunAnimation() {
        int step = 198;
        int x = 0;
        int y = 0;
        for (int i = 0; i < gun.length; ++i) {
            TextureRegion txtreg = new TextureRegion(gunTexture, x, y, step, 143);
            gun[i] = txtreg;
            if (x >= 2 * step) {
                x = 0;
            } else {
                x += step;
            }
        }
        gunAnimation = new Animation(0.05f, gun);
        gunAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }
//    public static void reloadGunAnimation() {
//        for (int i = 0; i < reload.length; ++i) {
//            Texture txt = new Texture("reload"+i+".png");
//
//            reload[i] = txt;
//        }
//        reloadAnimation = new Animation(0.1f, reload);
//        reloadAnimation.setPlayMode(Animation.PlayMode.NORMAL);
//    }

    public static void dispose() {
        points.dispose();
        gunTexture.dispose();
    }

}
