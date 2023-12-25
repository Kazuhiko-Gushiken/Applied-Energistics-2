// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
// package appeng.client.gui.layout;
package appeng.client.gui.layout;

public final class SupportCode {
    /**
     * Parses text that might contain mnemonic and returns structure which contains
     * plain text and index of mnemonic char (if any)
     */
    public static TextWithMnemonic parseText(String textWithMnemonic) {
        if (textWithMnemonic == null) {
            throw new IllegalArgumentException("textWithMnemonic cannot be null");
        }

        // Parsing is copied from Presentation.setText(String, boolean)
        int index = -1;
        final StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < textWithMnemonic.length(); i++) {
            char ch = textWithMnemonic.charAt(i);
            if (ch == '&') {
                i++;
                if (i >= textWithMnemonic.length()) {
                    break;
                }
                ch = textWithMnemonic.charAt(i);
                if (ch != '&') {
                    index = plainText.length();
                }
            }
            plainText.append(ch);
        }

        return new TextWithMnemonic(plainText.toString(), index);
    }

    public static final class TextWithMnemonic {
        /**
         * Plain text
         */
        public final String myText;
        /**
         * Index of mnemonic char. -1 means that text doesn't contain mnemonic char
         */
        public final int myMnemonicIndex;

        private TextWithMnemonic(final String text, final int index) {
            if (text == null) {
                throw new IllegalArgumentException("text cannot be null");
            }
            if (index != -1 && (index < 0 || index >= text.length())) {
                throw new IllegalArgumentException("wrong index: " + index + "; text = '" + text + "'");
            }
            myText = text;
            myMnemonicIndex = index;
        }

        public char getMnemonicChar() {
            if (myMnemonicIndex == -1) {
                throw new IllegalStateException("text doesn't contain mnemonic");
            }
            return Character.toUpperCase(myText.charAt(myMnemonicIndex));
        }
    }
}