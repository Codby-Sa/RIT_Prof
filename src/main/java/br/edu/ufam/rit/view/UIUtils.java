package br.edu.ufam.rit.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;

/**
 * Classe utilitária para aplicar estilos reutilizáveis em componentes Swing.
 */
public final class UIUtils {

    private UIUtils() {
    }

    /**
     * Cria um painel com aparência de card.
     *
     * @return painel estilizado
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(AppTheme.CARD_BACKGROUND);

        Border outer = BorderFactory.createLineBorder(AppTheme.BORDER);
        Border inner = BorderFactory.createEmptyBorder(16, 16, 16, 16);

        panel.setBorder(BorderFactory.createCompoundBorder(outer, inner));

        return panel;
    }

    /**
     * Aplica estilo padrão em uma tabela.
     *
     * @param table tabela que receberá o estilo
     */
    public static void styleTable(JTable table) {
        table.setFont(AppTheme.TABLE_FONT);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setSelectionBackground(AppTheme.TABLE_SELECTION);
        table.setSelectionForeground(AppTheme.TEXT);

        JTableHeader header = table.getTableHeader();
        header.setFont(AppTheme.TABLE_HEADER_FONT);
        header.setBackground(AppTheme.PRIMARY);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 34));
    }

    /**
     * Esconde uma coluna de uma tabela.
     *
     * @param table       tabela
     * @param columnIndex índice da coluna
     */
    public static void hideColumn(JTable table, int columnIndex) {
        table.getColumnModel().getColumn(columnIndex).setMinWidth(0);
        table.getColumnModel().getColumn(columnIndex).setMaxWidth(0);
        table.getColumnModel().getColumn(columnIndex).setWidth(0);
    }

    /**
     * Aplica estilo de botão principal.
     *
     * @param button botão
     */
    public static void stylePrimaryButton(JButton button) {
        styleButtonBase(button);
        button.setBackground(AppTheme.PRIMARY);
        button.setForeground(Color.WHITE);
    }

    /**
     * Aplica estilo de botão secundário.
     *
     * @param button botão
     */
    public static void styleSecondaryButton(JButton button) {
        styleButtonBase(button);
        button.setBackground(Color.WHITE);
        button.setForeground(AppTheme.PRIMARY);
        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(AppTheme.PRIMARY),
                        BorderFactory.createEmptyBorder(8, 14, 8, 14)));
    }

    /**
     * Aplica estilo de botão de perigo/exclusão.
     *
     * @param button botão
     */
    public static void styleDangerButton(JButton button) {
        styleButtonBase(button);
        button.setBackground(AppTheme.DANGER);
        button.setForeground(Color.WHITE);
    }

    /**
     * Aplica estilo base usado por todos os botões.
     *
     * @param button botão
     */
    private static void styleButtonBase(JButton button) {
        button.setFont(AppTheme.BUTTON_FONT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(8, 14, 8, 14));
    }
}