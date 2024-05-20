import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetDetailsComponent } from 'app/entities/dataset-details/dataset-details.component';
import { DatasetDetailsService } from 'app/entities/dataset-details/dataset-details.service';
import { DatasetDetails } from 'app/shared/model/dataset-details.model';

describe('Component Tests', () => {
  describe('DatasetDetails Management Component', () => {
    let comp: DatasetDetailsComponent;
    let fixture: ComponentFixture<DatasetDetailsComponent>;
    let service: DatasetDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetDetailsComponent],
        providers: [],
      })
        .overrideTemplate(DatasetDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DatasetDetails(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.datasetDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
