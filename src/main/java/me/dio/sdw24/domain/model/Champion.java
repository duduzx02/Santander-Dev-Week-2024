package me.dio.sdw24.domain.model;

public record Champion(
        Long id,
        String name,
        String role,
        String lore,
        String imageUrl
) {

    public String generateContextByQuestion(String question){

        return  """
                Nome do Campeão: %s
                Função: %s
                Lore (História): %s
                """.formatted(this.name, this.role, this.lore);
    }
}