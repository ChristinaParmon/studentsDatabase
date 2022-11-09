package student.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class StudentEntity {
    private long id;
    private String firstName;
    private String secondName;
    private long groupId;

    public StudentEntity(long id, String firstName, String secondName, long groupId) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.groupId = groupId;
    }

    public StudentEntity() {
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StudentEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("first name=" + firstName)
                .add("second name=" + secondName)
                .add("group Id=" + groupId)
                .toString();
    }

    @Override
    public boolean equals(Object anotherStudent) {
        if (this == anotherStudent) return true;
        if (anotherStudent == null || getClass() != anotherStudent.getClass()) return false;
        StudentEntity student = (StudentEntity) anotherStudent;
        return id == student.id
                && groupId == student.groupId
                && Objects.equals(firstName, student.firstName)
                && Objects.equals(secondName, student.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, groupId);
    }
}
