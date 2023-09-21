package telran.employees.dto;

import java.util.concurrent.locks.Lock;

public record Monitor(Lock read, Lock write) {

}
