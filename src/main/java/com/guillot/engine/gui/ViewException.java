package com.guillot.engine.gui;

import static com.guillot.engine.configs.EngineConfig.HEIGHT;
import static com.guillot.engine.configs.EngineConfig.WIDTH;
import static com.guillot.engine.configs.GUIConfig.DIALOG_BUTTON_TEXT;
import static com.guillot.engine.configs.GUIConfig.EXCEPTION_TEXT_COLOR;

import org.apache.log4j.Logger;

public class ViewException extends View {

    private final static Logger logger = Logger.getLogger(ViewException.class);

    private Exception exception;

    public ViewException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public void start() throws Exception {
        logger.error("", exception);

        String text = "";
        for (StackTraceElement trace : exception.getStackTrace()) {
            text += "\n     " + trace.toString();
        }

        Button button = new Button(DIALOG_BUTTON_TEXT, WIDTH - 256, HEIGHT - 64, 240, 48);
        button.setEvent(new Event() {

            public void perform() {
                GUI.get().getContainer().exit();
            }
        });

        add(button, new Text("Exception: " + exception.getMessage(), 30, 30, GUI.get().getFont(0), EXCEPTION_TEXT_COLOR),
                new Text(text, 30, 50));
    }
}
