select distinct childConcept.concept_id as id,childConcept.concept_full_name as name, childConceptRaw.is_set as isset, COALESCE (cn.concept_full_name ,cn.concept_short_name, cv.code) as title
from concept_view parentConcept
  inner join concept_set cs on parentConcept.concept_id = cs.concept_set
  inner join concept_view childConcept on cs.concept_id=childConcept.concept_id
  inner join concept childConceptRaw on childConcept.concept_id = childConceptRaw.concept_id
  left outer join concept_reference_term_map_view cv on (cv.concept_id = childConceptRaw.concept_id  and cv.concept_map_type_name = 'SAME-AS' and cv.concept_reference_source_name = 'EndTB-Export')
  left outer join concept_view  cn on (cn.concept_id = childConceptRaw.concept_id)
where childConceptRaw.is_set = 1
order by cs.sort_weight;