package org.app.zoo.dashboard;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.app.zoo.animal.AnimalRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService{

    private final AnimalRepository animalRepository;

    public DashboardService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    public Map<String, Integer> getEntriesPerMonthByYear(int year) {
        // Calcular el primer y último día del año
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        List<Object[]> results = animalRepository.findEntriesPerMonthByYear(startDate, endDate);
        
        // Crear un mapa para almacenar los resultados
        Map<String, Integer> entriesPerMonth = new LinkedHashMap<>();
        
        // Inicializar el mapa con meses en español
        String[] months = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", 
            "Junio", "Julio", "Agosto", "Septiembre", 
            "Octubre", "Noviembre", "Diciembre"
        };
        
        // Inicializar todos los meses con cero
        for (String m : months) {
            entriesPerMonth.put(m, 0);
        }

        // Procesar los resultados y llenar el mapa
        for (Object[] result : results) {
            int month = (int) result[0]; // Obtener el mes
            int count = ((Long) result[1]).intValue(); // Obtener el conteo
            
            // Asignar el conteo al mes correspondiente
            entriesPerMonth.put(months[month - 1], count); // -1 porque los meses son de 1 a 12
        }

        return entriesPerMonth;
    }

    public Map<String, Integer> getTopSpecies() {
        List<Object[]> results = animalRepository.findTopSpecies();
        
        // Crear un mapa para almacenar los resultados
        Map<String, Integer> topSpeciesMap = new LinkedHashMap<>();
        
        // Procesar los resultados y llenar el mapa
        for (Object[] result : results) {
            String speciesName = (String) result[0]; // Obtener el nombre de la especie
            int count = ((Long) result[1]).intValue(); // Obtener el conteo
            
            // Asignar el conteo al nombre de la especie correspondiente
            topSpeciesMap.put(speciesName, count);
        }

        return topSpeciesMap;
    }
}