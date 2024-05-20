import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetIndicationComponent } from 'app/entities/dataset-indication/dataset-indication.component';
import { DatasetIndicationService } from 'app/entities/dataset-indication/dataset-indication.service';
import { DatasetIndication } from 'app/shared/model/dataset-indication.model';

describe('Component Tests', () => {
  describe('DatasetIndication Management Component', () => {
    let comp: DatasetIndicationComponent;
    let fixture: ComponentFixture<DatasetIndicationComponent>;
    let service: DatasetIndicationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetIndicationComponent],
        providers: [],
      })
        .overrideTemplate(DatasetIndicationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetIndicationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetIndicationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DatasetIndication(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.datasetIndications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
