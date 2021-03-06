package org.androidtransfuse.model.manifest;

import org.androidtransfuse.annotations.LabeledEnum;

/**
 * @author John Ericksen
 */
public enum ProtectionLevel implements LabeledEnum {
    NORMAL("normal"),
    DANGEROUS("dangerous"),
    SIGNATURE("signature"),
    SIGNATUREORSYSTEM("signatureOrSystem");

    private String label;

    private ProtectionLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
