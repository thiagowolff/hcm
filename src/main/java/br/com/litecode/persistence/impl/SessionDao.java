package br.com.litecode.persistence.impl;

import br.com.litecode.domain.PatientSession;
import br.com.litecode.domain.Session;
import br.com.litecode.persistence.AbstractDao;
import org.joda.time.LocalDateTime;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class SessionDao extends AbstractDao<Session> {
	public List<Session> findChamberSessionsByDate(Integer chamberId, Date date) {
		String qlString = "select distinct s from Session s left join fetch s.patientSessions where s.chamber.chamberId = :chamberId and s.sessionTime between :startOfDay and :endOfDay order by s.sessionTime";

		TypedQuery<Session> query = entityManager.createQuery(qlString, Session.class);
		query.setParameter("chamberId", chamberId);
		query.setParameter("startOfDay", LocalDateTime.fromDateFields(date).withTime(0, 0, 0, 0).toDate());
		query.setParameter("endOfDay", LocalDateTime.fromDateFields(date).withTime(23, 59, 59, 999).toDate());

		return query.getResultList();
	}

	public List<Session> findSessionsByDate(Date date) {
		String qlString = "select s from Session s where s.sessionTime between :startOfDay and :endOfDay order by s.sessionTime";

		TypedQuery<Session> query = entityManager.createQuery(qlString, Session.class);
		query.setParameter("startOfDay", LocalDateTime.fromDateFields(date).withTime(0, 0, 0, 0).toDate());
		query.setParameter("endOfDay", LocalDateTime.fromDateFields(date).withTime(23, 59, 59, 999).toDate());

		return query.getResultList();
	}

	public List<Session> findChamberSessionsByPeriod(Integer chamberId, Date sessionTime) {
		String qlString = "select s from Session s where s.chamber.chamberId = :chamberId and s.sessionTime = :sessionTime";

		TypedQuery<Session> query = entityManager.createQuery(qlString, Session.class);
		query.setParameter("chamberId", chamberId);
		query.setParameter("sessionTime", sessionTime);

		return query.getResultList();
	}

	public Long countSessionsPerPatient(Integer patientId) {
		String qlString = "select count(*) from Session s join s.patientSessions ps where ps.patient.patientId = :patientId and s.status = 'FINISHED' and ps.status = 'ACTIVE'" ;

		TypedQuery<Long> query = entityManager.createQuery(qlString, Long.class);
		query.setParameter("patientId", patientId);

		try {
			return query.getSingleResult();
		} catch(NoResultException e) {
			return 0L;
		}
	}

	public Session findSessionById(Integer sessionId) {
		String qlString = "select distinct s from Session s join fetch s.patientSessions where s.sessionId = :sessionId";
		TypedQuery<Session> query = entityManager.createQuery(qlString, Session.class);
		query.setParameter("sessionId", sessionId);
		return query.getSingleResult();
	}

	public void insertPatientSession(PatientSession patientSession) {
		entityManager.persist(patientSession);
	}

	public void updatePatientSession(PatientSession patientSession) {
		entityManager.merge(patientSession);
	}

	public void deletePatientSession(PatientSession patientSession) {
		entityManager.remove(entityManager.find(PatientSession.class, patientSession.getId()));
	}
}
