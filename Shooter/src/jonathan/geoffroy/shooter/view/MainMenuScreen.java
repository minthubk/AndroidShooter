package jonathan.geoffroy.shooter.view;

import java.util.ArrayList;

import jonathan.geoffroy.shooter.Shooter;
import jonathan.geoffroy.shooter.view.utils.App;
import jonathan.geoffroy.shooter.view.utils.StageScreen;
import jonathan.geoffroy.shooter.view.utils.WallpaperActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenuScreen extends StageScreen {
	private final static String WALLPAPER = Shooter.IMAGES + "MainMenu/wallpaper.png", BACKGROUND = Shooter.IMAGES + "MainMenu/background.png";
	private final static String FONT = Shooter.FONTS + "MainMenu/font.fnt";
	private final static int NB_MENUS = 4;

	private Texture wallpaper;
	private Table menu;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList<AssetDescriptor<Object>> getAssetDescriptors() {
		ArrayList<AssetDescriptor<Object>> result = new ArrayList<AssetDescriptor<Object>>();
		result.add(new AssetDescriptor(WALLPAPER, Texture.class));
		result.add(new AssetDescriptor(BACKGROUND, Texture.class));
		result.add(new AssetDescriptor(FONT, BitmapFont.class));
		return result;
	}

	@Override
	protected void onEndLoaded() {
		float widthMenu = Gdx.graphics.getWidth() * 2 / 3;
		float heightMenu = (Gdx.graphics.getHeight() / NB_MENUS) * 2 / 3;

		menu = new Table();
		menu.setSize(Gdx.graphics.getWidth() * 2 / 3, Gdx.graphics.getHeight() * 2 / 3);

		Texture background = (Texture) App.getAsset(BACKGROUND);
		TextureRegion backgroundRegion = new TextureRegion(background, 256, 42);
		BitmapFont font = (BitmapFont) App.getAsset(FONT);
		wallpaper = (Texture) App.getAsset(WALLPAPER);

		TextButtonStyle style = new TextButtonStyle();
		style.up = new TextureRegionDrawable(backgroundRegion);
		style.font = font;

		TextButton level = new TextButton("MODE NIVEAU", style);
		level.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("mode niveau");
			}
		});
		menu.add(level).width(widthMenu).height(heightMenu);
		menu.row();

		TextButton unlimited = new TextButton("MODE ILLIMITE", style);
		unlimited.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("mode illimité");
			}
		});
		menu.add(unlimited).width(widthMenu).height(heightMenu);;
		menu.row();

		TextButton options = new TextButton("OPTIONS", style);
		options.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("options");
			}
		});
		menu.add(options).width(widthMenu).height(heightMenu);;
		menu.row();

		TextButton exit = new TextButton("QUITTER", style);
		exit.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
		menu.add(exit).width(widthMenu).height(heightMenu);;
		menu.row();

		menu.pack();
		menu.setPosition(Gdx.graphics.getWidth() / 2 - menu.getWidth() / 2, Gdx.graphics.getHeight() / 2 - menu.getHeight() / 2);

		int switcher = 0;
		float x, y;
		for(Actor actor : menu.getChildren()) {
			actor.addAction(Actions.moveTo(actor.getX(), actor.getY(), 1.0f, Interpolation.swing));

			if(switcher % 2 == 0) {
				x = - Gdx.graphics.getWidth() / 2;
			}
			else {
				x = Gdx.graphics.getWidth() / 2;
			}
			y = actor.getY();
			actor.setPosition(x, y);

			switcher++;
		}

		stage.addActor(new WallpaperActor(wallpaper));
		stage.addActor(menu);
	}
}