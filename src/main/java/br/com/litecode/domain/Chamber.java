package br.com.litecode.domain;

import br.com.litecode.domain.ChamberEvent.EventType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "chamber")
@Cacheable
public class Chamber implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chamber_id")
	private Integer chamberId;

	@Column
	private String name;

	@Column(name = "capacity")
	private Integer capacity;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "chamber_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private List<ChamberEvent> chamberEvents;

	public Integer getChamberId() {
		return chamberId;
	}

	public String getName() {
		return name;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public ChamberEvent getChamberEvent(EventType eventType) {
		Optional<ChamberEvent> chamberEventEvent = chamberEvents.stream().filter(event -> event.getEventType() == eventType).findFirst();
		if (chamberEventEvent.isPresent()) {
			return chamberEventEvent.get();
		}
		throw new RuntimeException("The chamber " + eventType + " event is not defined!");
	}

	public List<ChamberEvent> getChamberEvents() {
		return chamberEvents;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Chamber chamber = (Chamber) o;
		return chamberId != null ? chamberId.equals(chamber.chamberId) : chamber.chamberId == null;
	}

	@Override
	public int hashCode() {
		return chamberId.hashCode();
	}

	@Override
	public String toString() {
		return "[" + chamberId + "] " + name;
	}
}
