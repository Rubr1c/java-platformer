package io.github.rubr1c.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.rubr1c.entites.Player;
import io.github.rubr1c.fonts.Fonts;
import io.github.rubr1c.input.InputHandler;
import io.github.rubr1c.inventory.Items;
import io.github.rubr1c.registries.ItemRegistry;
import io.github.rubr1c.render.Camera;
import io.github.rubr1c.render.Renderer;
import io.github.rubr1c.util.Pos;
import io.github.rubr1c.util.Settings;
import io.github.rubr1c.util.TextureUtil;
import io.github.rubr1c.world.Border;
import io.github.rubr1c.world.InfinitePlatform;
import io.github.rubr1c.world.Platform;
import io.github.rubr1c.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameScreen extends BaseScreen {
    private Player player;
    private InfinitePlatform ground;
    private List<Platform> obs;
    private Camera camera;
    private Renderer renderer;
    private BitmapFont font;
    private final Levels currentLevel;
    private Texture background;
    private Map<Pos, Boolean> checkpoints;
    private Pos currentCheckpoint;
    private Texture checkpointTexture;
    private Texture activeCheckpointTexture;
    private static final int CHECKPOINT_SIZE = 30; // Size of checkpoint marker

    public GameScreen(Main game, int levelNumber) {
        super(game);
        this.currentLevel = Levels.LEVEL_1;
        initialize();
    }

    public GameScreen(Main game) {
        this(game, 1); // Default to level 1
    }

    private void initialize() {
        camera = new Camera();
        renderer = new Renderer();

        player = new Player();
        player.setPos(currentLevel.getPlayerStartX(), currentLevel.getPlayerStartY());

        ground = new InfinitePlatform(Color.GREEN);
        obs = currentLevel.createObstacles();
        obs.addAll(currentLevel.createBorders());

        checkpoints = currentLevel.getCheckpoints();
        currentCheckpoint = currentLevel.getPlayerStartPos();
        currentLevel.initializeItems(player);
        background = currentLevel.getBackground();

        // Load checkpoint textures
        try {
            checkpointTexture = TextureUtil.loadFrom("images/checkpoint.png");
            activeCheckpointTexture = TextureUtil.loadFrom("images/checkpoint_active.png");
        } catch (Exception e) {
            // If textures can't be loaded, create simple colored textures
            checkpointTexture = TextureUtil.createColorTexture(Color.YELLOW, 1, 1);
            activeCheckpointTexture = TextureUtil.createColorTexture(Color.GREEN, 1, 1);
        }

        font = Fonts.MINECRAFT_SM.get();
    }

    /**
     * Check if player has reached any checkpoints and update their status
     */
    private void updateCheckpoints() {
        for (Map.Entry<Pos, Boolean> entry : checkpoints.entrySet()) {
            Pos checkpointPos = entry.getKey();
            boolean isActive = entry.getValue();

            // Check if player is close enough to activate the checkpoint
            float distance = calculateDistance(player.getPos(), checkpointPos);
            if (distance < CHECKPOINT_SIZE && !isActive) {
                // Activate this checkpoint
                entry.setValue(true);
                // Create a new Pos object to avoid reference issues
                currentCheckpoint = new Pos(checkpointPos.x, checkpointPos.y);

                // Optional: Add visual or sound effect when activating checkpoint
                System.out.println("Checkpoint activated at: " + checkpointPos.x + ", " + checkpointPos.y);
            }
        }
    }

    /**
     * Calculate distance between two positions
     */
    private float calculateDistance(Pos pos1, Pos pos2) {
        float dx = pos1.x - pos2.x;
        float dy = pos1.y - pos2.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Render all checkpoints
     */
    private void renderCheckpoints() {
        // Set the projection matrix to use camera coordinates
        renderer.setProjectionMatrixToCamera(camera.getCamera());

        for (Map.Entry<Pos, Boolean> entry : checkpoints.entrySet()) {
            Pos checkpointPos = entry.getKey();
            boolean isActive = entry.getValue();

            // Draw the checkpoint with appropriate texture
            Texture textureToUse = isActive ? activeCheckpointTexture : checkpointTexture;

            // Check if this is the current checkpoint
            boolean isCurrent = (currentCheckpoint != null) &&
                    Math.abs(checkpointPos.x - currentCheckpoint.x) < 0.1f &&
                    Math.abs(checkpointPos.y - currentCheckpoint.y) < 0.1f;

            // Draw the checkpoint with a pulsing effect if it's the current one
            float scale = 1.0f;
            if (isCurrent) {
                // Create a pulsing effect for the current checkpoint
                scale = 1.0f + 0.2f * (float) Math.sin(Gdx.graphics.getFrameId() * 0.1f);
            }

            int size = (int) (CHECKPOINT_SIZE * scale);

            // Draw the checkpoint at its world position
            renderer.drawTextureWithCamera(
                    textureToUse,
                    checkpointPos.x - size / 2, // Center the checkpoint
                    checkpointPos.y - size / 2,
                    size,
                    size);
        }
    }

    @Override
    public void render(float delta) {
        // Handle player input
        InputHandler.HandlePlayer(player);

        // Handle checkpoint teleportation
        InputHandler.HandleCheckpointTeleport(player, currentCheckpoint);

        // Update checkpoints based on player position
        updateCheckpoints();

        // Update camera and ground
        renderer.setFont(font);
        camera.update(player);
        ground.update(camera.getCamera());

        // Render game elements
        renderer.render(background, camera.getCamera(), ground, obs, player);

        // Render checkpoints with proper camera projection
        renderCheckpoints();

        // Render debug info
        Settings.renderCoordinates(player, camera);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        camera.dispose();
        if (checkpointTexture != null)
            checkpointTexture.dispose();
        if (activeCheckpointTexture != null)
            activeCheckpointTexture.dispose();
    }
}
