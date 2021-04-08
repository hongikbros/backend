package com.hongikbros.jobmanager.common.core.validation;

import javax.validation.GroupSequence;

@GroupSequence({FirstValid.class, SecondValid.class})
public interface ValidationSequence {
}
