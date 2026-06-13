package br.edu.ufam.rit.view;

import java.awt.Color;
import java.awt.Font;

/**
 * Classe responsável por centralizar cores e fontes usadas na interface.
 *
 * <p>Com essa classe, o sistema mantém uma identidade visual consistente
 * em todas as telas.</p>
 */
public final class AppTheme {

    public static final Color BACKGROUND = new Color(245, 247, 250);
    public static final Color CARD_BACKGROUND = Color.WHITE;

    public static final Color PRIMARY = new Color(31, 78, 121);
    public static final Color PRIMARY_DARK = new Color(22, 59, 92);
    public static final Color DANGER = new Color(176, 0, 32);

    public static final Color TEXT = new Color(51, 51, 51);
    public static final Color MUTED_TEXT = new Color(105, 115, 125);
    public static final Color BORDER = new Color(217, 226, 236);
    public static final Color TABLE_SELECTION = new Color(221, 235, 247);

    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    public static final Font SUBTITLE_FONT = new Font("Arial", Font.PLAIN, 14);
    public static final Font SECTION_TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    public static final Font BODY_FONT = new Font("Arial", Font.PLAIN, 13);
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 13);
    public static final Font TABLE_FONT = new Font("Arial", Font.PLAIN, 13);
    public static final Font TABLE_HEADER_FONT = new Font("Arial", Font.BOLD, 13);

    private AppTheme() {
    }
}