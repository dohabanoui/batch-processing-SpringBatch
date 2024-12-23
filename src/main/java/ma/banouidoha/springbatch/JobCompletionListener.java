package ma.banouidoha.springbatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobCompletionListener implements JobExecutionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionListener.class);
    // Utilisé pour exécuter des requêtes SQL sur la base de données
    private final JdbcTemplate jdbcTemplate;

    public JobCompletionListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("Le job s'est terminé avec succès.");

            // Récupérer les données depuis la base de données
            List<Order> orders = jdbcTemplate.query(
                    "SELECT * FROM orders",
                    new DataClassRowMapper<>(Order.class)
            );

            if (orders.isEmpty()) {
                LOGGER.info("Aucune commande n'a été trouvée dans la base de données.");
            } else {
                orders.forEach(order -> LOGGER.info("Commande insérée : {}",order.toString()));
            }
        } else {
            LOGGER.error("Le job s'est terminé avec des erreurs. Statut : {}", jobExecution.getStatus());
        }
    }
}
