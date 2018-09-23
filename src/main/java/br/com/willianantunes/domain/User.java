package br.com.willianantunes.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = User.COLLECTION_NAME)
public class User implements Serializable {

    public static final String COLLECTION_NAME = "wa_user";

    @Id
    private ObjectId id;
    @NotBlank
    private String name;
    @Email
    @NotNull
    @Indexed(unique = true)
    private String email;
    @NotNull
    @Size(min = 60, max = 60)
    private String password;
    private List<Product> products;
}