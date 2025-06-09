CREATE SEQUENCE seq_client_id
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE client (
                        id BIGINT PRIMARY KEY,
                        last_name VARCHAR(255),
                        first_name VARCHAR(255),
                        phone_number VARCHAR(255) UNIQUE
);

CREATE SEQUENCE seq_claim_id
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE claim (
                       id BIGINT PRIMARY KEY,
                       status VARCHAR(255),
                       client_id BIGINT,
                       CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client (id)
);

CREATE SEQUENCE seq_claim_history_id
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE claim_history (
                               id BIGINT PRIMARY KEY,
                               created_at TIMESTAMP,
                               created_by VARCHAR(255),
                               old_status VARCHAR(255),
                               new_status VARCHAR(255),
                               claim_id BIGINT,
                               CONSTRAINT fk_claim FOREIGN KEY (claim_id) REFERENCES claim (id)
);

CREATE SEQUENCE seq_break_reason_id
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE break_reason (
                              id INT PRIMARY KEY,
                              reason VARCHAR(255),
                              processing_time BIGINT,
                              repair_time BIGINT
);
