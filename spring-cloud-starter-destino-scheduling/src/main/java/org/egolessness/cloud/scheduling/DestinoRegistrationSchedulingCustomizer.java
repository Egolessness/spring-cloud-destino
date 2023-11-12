package org.egolessness.cloud.scheduling;

import org.egolessness.cloud.context.DestinoRegistrationCustomizer;
import org.egolessness.destino.client.registration.message.RegistrationInfo;
import org.egolessness.destino.client.scheduling.functional.Scheduled;
import org.egolessness.destino.common.utils.PredicateUtils;
import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * Destino registration scheduling customizer.
 *
 * @author zsmjwk@outlook.com (wangkang)
 */
public class DestinoRegistrationSchedulingCustomizer implements DestinoRegistrationCustomizer {

    private final DestinoSchedulingJobScanner jobScanner;

    public DestinoRegistrationSchedulingCustomizer(DestinoSchedulingJobScanner jobScanner) {
        this.jobScanner = jobScanner;
    }

    @Override
    public void accept(@NonNull RegistrationInfo registration) {
        Set<Scheduled<String, String>> jobs = jobScanner.loadJobs();
        if (PredicateUtils.isNotEmpty(jobs)) {
            registration.setJobs(jobs);
        }
    }

}
