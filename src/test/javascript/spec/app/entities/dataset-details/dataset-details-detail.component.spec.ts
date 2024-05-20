import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetDetailsDetailComponent } from 'app/entities/dataset-details/dataset-details-detail.component';
import { DatasetDetails } from 'app/shared/model/dataset-details.model';

describe('Component Tests', () => {
  describe('DatasetDetails Management Detail Component', () => {
    let comp: DatasetDetailsDetailComponent;
    let fixture: ComponentFixture<DatasetDetailsDetailComponent>;
    const route = { data: of({ datasetDetails: new DatasetDetails(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DatasetDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.datasetDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
