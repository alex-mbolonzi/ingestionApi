import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetStudyComponent } from 'app/entities/dataset-study/dataset-study.component';
import { DatasetStudyService } from 'app/entities/dataset-study/dataset-study.service';
import { DatasetStudy } from 'app/shared/model/dataset-study.model';

describe('Component Tests', () => {
  describe('DatasetStudy Management Component', () => {
    let comp: DatasetStudyComponent;
    let fixture: ComponentFixture<DatasetStudyComponent>;
    let service: DatasetStudyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetStudyComponent],
        providers: [],
      })
        .overrideTemplate(DatasetStudyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetStudyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetStudyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DatasetStudy(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.datasetStudies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
