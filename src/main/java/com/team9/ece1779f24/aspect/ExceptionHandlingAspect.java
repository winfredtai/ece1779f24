/*
package com.team9.ece1779f24.aspect;

import com.team9.ece1779f24.enums.Status;
import com.team9.ece1779f24.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionHandlingAspect {

    @Around("@annotation(org.springframework.context.event.EventListener)") // Target methods annotated with @EventListener
    public Object handleExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // Log method entry
            log.info("Entering method: {}", joinPoint.getSignature());
            // Proceed with the actual method execution
            Object result = joinPoint.proceed();
            log.info("Exiting method: {}", joinPoint.getSignature());
            return result;
        } catch (DataAccessException dae) {
            log.error("Database error in method: {}", joinPoint.getSignature(), dae);
            throw new ApplicationException(Status.DATABASE_ERROR);
        } catch (IllegalArgumentException iae) {
            log.error("Invalid input in method: {}", joinPoint.getSignature(), iae);
            throw new ApplicationException(Status.INVALID_INPUT);
        }
        catch (NullPointerException npe) {
            log.error("Null value found in method: {}", joinPoint.getSignature(), npe);
            throw new ApplicationException(Status.MISSING_FIELD);
        }
        catch (Exception e) {
            log.error("Unexpected error in method: {}", joinPoint.getSignature(), e);
            throw e;
        }
    }
}*/
