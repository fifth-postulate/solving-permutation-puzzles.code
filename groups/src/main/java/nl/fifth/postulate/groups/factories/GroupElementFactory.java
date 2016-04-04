package nl.fifth.postulate.groups.factories;


import nl.fifth.postulate.groups.GroupAction;
import nl.fifth.postulate.groups.GroupElement;

import java.util.List;

public interface GroupElementFactory<T extends GroupElement<T> & GroupAction> {
    T create(List<Integer> image, String symbol);
}
