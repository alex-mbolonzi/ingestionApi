import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetTherapyComponent } from 'app/entities/dataset-therapy/dataset-therapy.component';
import { DatasetTherapyService } from 'app/entities/dataset-therapy/dataset-therapy.service';
import { DatasetTherapy } from 'app/shared/model/dataset-therapy.model';

describe('Component Tests', () => {
  describe('DatasetTherapy Management Component', () => {
    let comp: DatasetTherapyComponent;
    let fixture: ComponentFixture<DatasetTherapyComponent>;
    let service: DatasetTherapyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetTherapyComponent],
        providers: [],
      })
        .overrideTemplate(DatasetTherapyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetTherapyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetTherapyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DatasetTherapy(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.datasetTherapies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
