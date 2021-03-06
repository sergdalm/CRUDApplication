package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "WRITER")
public class Writer {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @EqualsAndHashCode.Exclude
    @OneToMany
    @JoinTable(name = "WRITER_POSTS", joinColumns = {
            @JoinColumn(name = "WRITER_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "POST_ID")
    })
    @ToString.Exclude
    private List<Post> posts;

    public Writer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return id != null && Objects.equals(id, writer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void update(Writer writer) {
        this.firstName = writer.getFirstName();
        this.lastName = writer.getLastName();
        this.email = writer.getEmail();
        this.password = writer.getPassword();
    }
}
