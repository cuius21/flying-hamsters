package engine.render;


import engine.EngineException;
import engine.Transformation;
import engine.Utils;
import engine.essential.Window;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by Szymon Piechaczek on 18.12.2016.
 */
public class Renderer {
    private final Transformation transformation;
    private final ShaderProgram sProgram;
    public Renderer() throws EngineException {
        transformation = new Transformation();
        sProgram = new ShaderProgram();
    }

    public void init(Window window) throws Exception {
        GL.createCapabilities();
        sProgram.createVertexShader(Utils.loadResource("/vertex.glsl"));
        sProgram.createFragmentShader(Utils.loadResource("/simple_color.glsl"));
        sProgram.link();

        sProgram.createUniform("projectionMatrix");
        sProgram.createUniform("worldMatrix");
    }

    public void render(RenderItem[] items, Window window)
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        sProgram.bind();
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(window.getWidth(), window.getHeight());
        sProgram.setUniform("projectionMatrix", projectionMatrix);
        for (RenderItem item : items)
        {
            Matrix4f worldMatrix = transformation.getWorldMatrix(
                    item.getPosition(),
                    item.getRotation(),
                    item.getScale());
            sProgram.setUniform("worldMatrix", worldMatrix);
            item.getVertexBuffer().render();
        }
        sProgram.unbind();
    }

    public void cleanup(){
        if (sProgram != null) {
            sProgram.cleanup();
        }
    }
}
