package ru.stqa.mantis.manager.developermail;

import java.util.List;

public record GetIdentifiersResponse(Boolean success, Object errors, List<String> result) {

}
