package com.example.shop.serializators;

import com.example.shop.models.enums.Role;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RoleDeserializer extends JsonDeserializer<Set<Role>> {
    /*@Override
    public Set<Role> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String[] roles = jsonParser.getText().replace("[", "").replace("]", "").replace("\"", "").split(",");
        return Stream.of(roles)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }*/

    /*@Override
    public Set<Role> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        Set<Role> roles = new HashSet<>();
        String[] roleStrings = p.readValueAs(String[].class);
        for (String roleString : roleStrings) {
            roles.add(Role.valueOf(roleString));
        }
        return roles;
    }*/

    @Override
    public Set<Role> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Set<Role> roles = new HashSet<>();
        JsonNode node = p.getCodec().readTree(p);

        if (node.isArray()) {
            for (JsonNode roleNode : node) {
                roles.add(Role.valueOf(roleNode.asText()));
            }
        } else if (node.isTextual()) {
            roles.add(Role.valueOf(node.asText()));
        }

        return roles;
    }
}
