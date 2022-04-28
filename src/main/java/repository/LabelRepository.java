package repository;

import dto.LabelDto;
import model.Label;

import java.util.List;

public interface LabelRepository extends GenericRepository<Label, Integer> {

    void matchLabelWithPost(Label label, Integer postId);

    List<Label> getLabelsByPostId(Integer postId);
}
