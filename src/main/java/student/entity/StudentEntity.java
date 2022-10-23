package student.entity;

public class StudentEntity {
    int id;
    String firstName;
    String secondName;
    int groupId;

    public StudentEntity(int id, String firstName, String secondName, int groupId) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.groupId = groupId;
    }

    public StudentEntity() {
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getGroupId() {
        return groupId;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }



    @Override
    public String toString() {
        return "StudentEntity: " +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", groupId=" + groupId +
                '}';

    }
}
